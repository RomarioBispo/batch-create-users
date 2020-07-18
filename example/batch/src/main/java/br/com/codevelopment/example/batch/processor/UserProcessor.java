package br.com.codevelopment.example.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.entity.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProcessor implements ItemProcessor<Person, Student> {

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
		s.setYear(2020);
		s.setSemester(1);
		return s;
	}
}
