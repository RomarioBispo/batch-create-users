package br.com.codevelopment.example.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.entity.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProcessor implements ItemProcessor<Person, Student> {

	@Override
	public Student process(final Person person) throws Exception {

		Student s = new Student();
		log.debug("processor");
		return s;
	}
}
