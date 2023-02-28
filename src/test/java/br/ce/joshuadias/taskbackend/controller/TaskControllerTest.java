package br.ce.joshuadias.taskbackend.controller;

import java.time.LocalDate;

import br.ce.joshuadias.taskbackend.model.Task;
import br.ce.joshuadias.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.joshuadias.taskbackend.repo.TaskRepo;

import org.mockito.Mock;
import org.mockito.*;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSaveSucessefully() throws ValidationException {
		Task task = new Task(null, "Task", LocalDate.now());
		controller.save(task);
	}
	
	@Test
	public void shouldNotSaveWithoutTask() {
		Task task = new Task(null, null, LocalDate.now());
		try {
			controller.save(task);
			Assert.fail();
		} catch(ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void shouldNotSaveWithoutDate() {
		Task task = new Task(null, "Task", null);
		try {
			controller.save(task);
			Assert.fail();
		} catch(ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void shouldNotSaveWithPastDate() {
		Task task = new Task(null, "Task", LocalDate.of(2012, 01, 01));
		try {
			controller.save(task);
			Assert.fail();
		} catch(ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
}