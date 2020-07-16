package br.com.codevelopment.example.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.codevelopment.example.entity.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(final Person person) throws Exception {

		Person u = new Person();
		log.debug("processor");
		return u;
	}
}
