package br.com.codevelopment.example.decider;

import java.time.LocalDate;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import br.com.codevelopment.example.enums.JobParamsEnum;

@Component
public class SignUpJobDecider implements JobExecutionDecider {
	
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,
			org.springframework.batch.core.StepExecution stepExecution) {
		String year = jobExecution.getJobParameters().getString(JobParamsEnum.YEAR.getName());
		// signup user only in current year
		if (LocalDate.now().getYear() != Integer.parseInt(year)) {
			return new FlowExecutionStatus("DONT");
		}
		return new FlowExecutionStatus("EXECUTE");
	}
}
