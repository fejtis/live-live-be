# Testing Style Guide

This project uses JUnit 5 and a small set of shared test doubles. The goal is to keep tests readable, consistent, and easy to scan.

## Naming
- Test classes: `ClassNameTest`
- Test methods: `<action><ExpectedOutcome>` (verb phrase)
- Nested classes group tests by method or feature (e.g. `Constructor`, `Generate`, `Allows`, `Score`)

## Structure
- Use `@Nested` to group related tests.
- Keep helpers at the bottom of the class.
- Prefer explicit setup in each test over complex shared state.

Example:
```java
class ExampleServiceTest {
    @Nested
    class Execute {
        @Test
        void returnsResultWhenInputIsValid() {
            // arrange
            // act
            // assert
        }
    }
}
```

## Assertions
- Use JUnit Jupiter assertions (`org.junit.jupiter.api.Assertions`).
- Avoid mixing assertion libraries.

## Test doubles
- Reuse fakes from `src/test/java/com/of/ll/doubles` when possible.
- Extend existing fakes instead of creating new ones.

## Scope
- Unit tests should not require Spring context unless necessary.
- Integration tests should be explicit and clearly named.
