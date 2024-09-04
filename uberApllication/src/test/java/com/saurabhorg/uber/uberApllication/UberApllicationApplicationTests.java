package com.saurabhorg.uber.uberApllication;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

//@SpringBootTest
@Slf4j
class UberApllicationApplicationTests {

	@BeforeAll
	static void setUpOnce() {
		log.info("Setup once only");
	}

	@BeforeEach
	void setUp() {
		log.info("Setting up config");
	}

	@AfterAll
	static void tearDownAll() {
		log.info("Tear down all once");
	}

	@AfterEach
	void tearDown() {
		log.info("Tearing down the setup");
	}

	@Test
//	@Disabled
	void testNumOne() {
//		log.info("Test 1 is run");
		int a = 3;
		int b = 5;

		int result = addTwoNums(a, b);
		log.info("result: {}", result);

		// Junit
		// Assertions.assertEquals(result, 8);
		// Assertions.assertEquals(result, 7);

		// AssertJ
		assertThat(result)
				.isEqualTo(8)
				.isCloseTo(10, Offset.offset(2));

		assertThat("Hello")
				.isEqualTo("Hello")
				.startsWith("He")
				.endsWith("lo")
				.hasSize(5);
	}

	@Test
	// @DisplayName("testNumTwo")
	void testNumTwo_whenDenominatorIsNotZero_thenTestFurther() {
	//	log.info("Test 2 is run");

		int a = 15;
		int b = 2;

		int result = divideTwoNums(a, b);
		log.info("divide-result: {}", result);

		assertThat(result)
				.isEqualTo(7);
	}

	@Test
	void testNumTwo_whenDenominatorIsZero_thenArithmeticException() {
		int a = 15;
		int b = 0;

		assertThatThrownBy(() -> divideTwoNums(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tried to divide by zero");
				// .isInstanceOf(NullPointerException.class);
	}


	int addTwoNums(int a, int b) {
		return a+b;
	}

	int divideTwoNums(int a, int b) {
		try {
			return a/b;
		} catch(Exception e) {
			log.info("Arithmetic Exception Occurred: {}", e.getLocalizedMessage());
			// throw new ArithmeticException(e.getLocalizedMessage());
			throw new ArithmeticException("Tried to divide by zero");
		}
	}
}
