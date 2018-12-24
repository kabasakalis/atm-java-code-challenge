# ATM Simulation Java Code Challenge
------------------------------------

[![Build Status](https://travis-ci.org/kabasakalis/atm-java-code-challenge.svg?branch=master)](https://travis-ci.org/kabasakalis/atm-java-code-challenge)   [![codecov](https://codecov.io/gh/kabasakalis/atm-java-code-challenge/branch/master/graph/badge.svg)](https://codecov.io/gh/kabasakalis/atm-java-code-challenge)

![Atm screenshot](https://github.com/kabasakalis/atm-java-code-challenge/blob/master/src/main/resources/atm.png)

### Challenge Requirements

The application simulates the Backend logic of a cash dispensing Automatic Teller Machine (ATM).

It should be able to simulate and report the out come of people requesting money.

No requirement for any authentication or PIN to access the ATM.

Needs to keep track of the current cash of the ATM, and dispense only the notes available.

It should be possible to tell it that it has so many of each type of note during initialisation. After initialisation, it is only possible to remove notes.

It must know how many of each type of bank note it has and it should be able to report back how much of each note it has.

It must support $20 and $50 notes.

It should be able to dispense only legal combinations of notes. For example, a request for $100 can be satisfied by either five $20 notes or two $50 notes. It is not required to present a list of options.

If a request cannot be satisfied due to failure to find a suitable combination of notes, it should report an error condition in some fashion. For example, in an ATM with only $20 and $50 notes, it is not possible to dispense $30.

Dispensing money should reduce the amount of available cash in the machine.

Failure to dispense money due to an error should not reduce the amount of available cash in the machine.

### Libraries used
- [Text IO](http://text-io.beryx.org/releases/latest/)
for better console interaction experience.

### Build Prerequisites
- [Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
in `build.gradle` file.
- [Gradle Build Tool, (latest version)](https://gradle.org/install/) to simplify compilation and dependency management.

### Build And Run the Application
In the root of the project, just run`gradle run`.

### Run the tests
In the root of the project, just run`gradle test --info`.
