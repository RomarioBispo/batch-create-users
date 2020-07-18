package br.com.codevelopment.example.batch.job;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import br.com.codevelopment.example.batch.listener.UserJobListener;
import br.com.codevelopment.example.batch.processor.UserProcessor;
import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.entity.Student;
import br.com.codevelopment.example.enums.JobParamsEnum;
import br.com.codevelopment.example.repository.PersonRepository;
import br.com.codevelopment.example.repository.StudentRepository;

@Configuration
public class SignUpUsersJob {
	
	public static final String STEP_NAME = "stepSignStudents";
	public static final String JOB_NAME = "jobSignStudents";
	
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
	public JobParametersValidator validatorSignUpJob() {

		// Adding the DATE_INCREMENTER parameter for enabling the rerun batches
		String[] optionalParams = new String[] { JobParamsEnum.DATE_INCREMENTER.getName() };
		String[] compulsoryParameters = new String[] { JobParamsEnum.YEAR.getName(), JobParamsEnum.SEMESTER.getName()};

		return new DefaultJobParametersValidator(compulsoryParameters, optionalParams);
	}

	@StepScope
	@Bean
	public UserProcessor processor() {
	  return new UserProcessor();
	}
	
	@Bean 
	public CompositeJobExecutionListener listeners() {
		CompositeJobExecutionListener compositeListeners =  new CompositeJobExecutionListener();
		compositeListeners.setListeners(Arrays.asList(new UserJobListener()) );
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
    public Step deleteAllStudentsStep() {
        return stepBuilderFactory.get("deleteAllStudentsStep")
                .tasklet((contribution, chunkContext) -> {
                	studentRepository.deleteAll();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    
	@Bean
	public Job signStudentsJob(Step stepSignStudents, Step deleteAllStudentsStep, JobExecutionDecider decider ) {
	  return jobBuilderFactory.get(JOB_NAME)
		.validator(validatorSignUpJob())
		.incrementer(new RunIdIncrementer())
		.listener(listeners())
		.flow(stepSignStudents)
	    .start(decider)
	    		.on("EXECUTE").to(deleteAllStudentsStep).next(stepSignStudents)
	    		.from(decider).on("DONT").end()
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
