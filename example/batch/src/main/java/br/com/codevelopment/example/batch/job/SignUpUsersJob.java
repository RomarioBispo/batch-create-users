package br.com.codevelopment.example.batch.job;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import br.com.codevelopment.example.batch.listener.UserJobListener;
import br.com.codevelopment.example.batch.processor.UserProcessor;
import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.entity.Student;
import br.com.codevelopment.example.repository.PersonRepository;
import br.com.codevelopment.example.repository.StudentRepository;

@Configuration
public class SignUpUsersJob {
	
	public static final String STEP_NAME = "jobSignStudents";
	public static final String JOB_NAME = "stepSignStudents";
	
	private JobBuilderFactory jobBuilderFactory;
	
	private StepBuilderFactory stepBuilderFactory;
	
	private PersonRepository personRepository; 
	
	private StudentRepository studentRepository;
	
	@Value("${job.page.size}")
	private int pageSize;
	
	@Value("${job.chunk.size}")
	private int chunkSize;
	
	public SignUpUsersJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, 
			PersonRepository personRepository, StudentRepository studentRepository) {
		this.jobBuilderFactory =jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.personRepository = personRepository;
		this.studentRepository = studentRepository;
	}
	
	@Bean
	public RepositoryItemReader<Person> reader() {
		RepositoryItemReader<Person> r = new RepositoryItemReader<>();
		r.setMethodName("findAll");
		r.setRepository(personRepository);
		r.setPageSize(pageSize);
		r.setSort(getSort());
		return r;
	}
	
	public Map<String, Sort.Direction> getSort() {
		HashMap<String, Sort.Direction> sort = new HashMap<>();
		sort.put("id", Sort.Direction.ASC);
		return sort;
	}

	@Bean
	public UserProcessor processor() {
	  return new UserProcessor();
	}
	
	@Bean 
	public CompositeJobExecutionListener listeners() {
		CompositeJobExecutionListener compositeListeners =  new CompositeJobExecutionListener();
		compositeListeners.setListeners(Arrays.asList(new JobExecutionListenerSupport[]{new UserJobListener()}) );
		return compositeListeners;
	}

	@Bean
	public RepositoryItemWriter<Student> writer() {
		RepositoryItemWriter<Student> w = new RepositoryItemWriter<>();
		w.setMethodName("save");
		w.setRepository(studentRepository);
		return w;
	}
	
	@Bean
	public Job signStudentsJob(Step stepSignStudents) {
	  return jobBuilderFactory.get(JOB_NAME)
	    .incrementer(new RunIdIncrementer())
	    .listener(listeners())
	    .flow(stepSignStudents)
	    .end()
	    .build();
	}

	@Bean
	public Step stepSignStudents(RepositoryItemWriter<Student> writer) {
	  return stepBuilderFactory.get(STEP_NAME)
	    .<Person, Student> chunk(chunkSize)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer)
	    .build();
	}
}
