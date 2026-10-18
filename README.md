# Java Task Manager

A console task manager written in Java for my Object Oriented Programming course.
Each user signs in, keeps their own task list and gets a reminder before a deadline.

## Features

- Sign in with a username and password (users are read from `users.csv`)
- Add, complete and delete tasks
- Two kinds of tasks: a normal `Task` with a deadline, and a `TimeAsk` with a start time and a duration
- List tasks sorted by deadline, overdue tasks, upcoming tasks and completed tasks
- A notification 30 minutes before each deadline
- Every change is saved to a CSV file per user (`tasks_<username>.csv`)

## Project structure

| Class | What it does |
|:--|:--|
| `Main` | Entry point, starts the app |
| `TaskApp` | Menu loop: sign in, load tasks, handle the user's choices |
| `User`, `UserCsvRepository` | Users and reading/writing `users.csv` |
| `Project` | Holds the tasks and answers questions like "which tasks are overdue?" |
| `Task`, `TimeAsk` | The two task types (`TimeAsk` extends `Task`) |
| `Completable` | Interface implemented by `Task` |
| `Deadline`, `Notification` | Due dates and the reminders built from them |
| `TaskCsvRepository` | Reading/writing a user's tasks |

## Running

Open the project in Eclipse (or any Java IDE) and run `src/Main/Main.java`.
The program reads and writes the CSV files in the working directory.

## Tests

The `Test` folder has 49 JUnit 5 tests that cover tasks, deadlines, notifications,
projects, users and both CSV repositories. Run them from the IDE with JUnit 5.
