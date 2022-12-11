### 1. Which of the following activities cannot be automated


- [ ] Test execution

- [x] Exploratory testing

- [x] Discussing testability issues

- [ ] Test data generation

 Automation can be used to help with Exploratory testing, but not the whole process

### 2. How do we describe a good unit test?


- [ ] Flawless, Ready, Self-healing, True, Irresistible

- [ ] Red, Green, Refactor

- [X] Fast, Repeatable, Self-validating, Timely, Isolated

- [ ] Tests should be dependent on other tests


### 3. When is it a good idea to use XPath selectors


- [ ] When CSS or other selectors are not an option or would be brittle and hard to maintain

- [ ] When we need to find an element based on parent/child/sibling relationship

- [ ] When an element is located deep within the HTML (or DOM) structure

- [X]  All the above


### 4. Describe the TDD process

1. Add a test to test some part of the functionality
2. Run all tests, new tests should all fail (since there is no working code)
3. Write code that is enough to pass the test
4. Run the tests, all tests should pass
5. Refactor if necessary (tests should still pass after refactoring)
6. Repeat steps 1-5 until your project is fully functional

### 5. Write 2 test cases or scenarios for a String Calculator application, which has a method calculate() that takes a string of two numbers separated by a comma as input, and returns the sum.
   
* Given two negative numbers as a string ("-1,-5"), When the method calculate() is called Then should see "-6" as a result.
* Given one negative and one positive number ("4, -8") When the method calculate() is called Then should see "-4" as a result.


FYI:
We have found a bug with OWM, where if you enter an empty string with whitespaces as a city name, it gives a weather report for Xankandi

