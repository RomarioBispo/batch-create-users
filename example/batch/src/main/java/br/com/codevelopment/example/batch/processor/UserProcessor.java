package br.com.codevelopment.example.batch.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.entity.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
public class UserProcessor implements ItemProcessor<Person, Student> {
	
	@Value("#{jobParameters['YEAR']}")
	private String year;
	
	@Value("#{jobParameters['SEMESTER']}")
	private String semester;
	
	
	@Override
	public Student process(final Person person) throws Exception {
		Student student = toStudent(person);
		log.debug("Converting {} into {}", person, student);
		return student;
	}
	
	/*
	 * Sign in all students into first semester of 2020
	 */
	private Student toStudent(Person p) {
		Student s = new Student();
		s.setName(p.getName());
		s.setLastName(p.getLastName());
		s.setRg(p.getRg());
		s.setCpf(p.getCpf());
		s.setBirthDate(p.getBirthDate());
		s.setYear(Integer.parseInt(year));
		s.setSemester(Integer.parseInt(semester));
		return s;
	}
}
