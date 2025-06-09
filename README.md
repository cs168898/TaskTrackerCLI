# Task Tracker CLI

**Task Tracker** is a simple command-line interface (CLI) application to help you manage your tasks efficiently. Track what you need to do, what you're working on, and what you've completed — all from your terminal.

---

##  Features

* Add new tasks
* Update existing tasks
* Delete tasks
* Mark tasks as "in-progress" or "done"
* List all tasks or filter them by status
* All tasks stored persistently in a local `tasks.json` file

---

## Requirements

* Java
* Terminal/Command Prompt

>  No external libraries or frameworks required — everything runs with native file system support.

---

## Installation & Setup

1. **Clone this repository**

   ```bash
   git clone https://github.com/cs168898/TaskTrackerCLI
   cd src/out/prod
   ```

2. **Compile the program (for Java)**

   ```bash
   javac *.java
   ```

3. **Run the CLI**

   ```bash
   java TaskTrackerCLI  [command] [arguments...]
   ```

> The app will create a `tasks.json` file in the current directory if it doesn't exist.

---

## Usage

### Add a New Task

```bash
TaskTrackerCLI add "Buy groceries"
```

Output: `Task added successfully (ID: 1)`

---

### Update an Existing Task

```bash
TaskTrackerCLI update 1 "Buy groceries and cook dinner"
```

---

### Delete a Task

```bash
TaskTrackerCLI delete 1
```

---

### Mark Task Status

```bash
TaskTrackerCLI mark-in-progress 1
TaskTrackerCLI mark-done 1
```

---

### List Tasks

#### All Tasks

```bash
TaskTrackerCLI list
```

#### Only Done Tasks

```bash
TaskTrackerCLI list done
```

#### Only Todo Tasks

```bash
TaskTrackerCLI list todo
```

#### Only In-Progress Tasks

```bash
TaskTrackerCLI list in-progress
```

---

## Task Format

Each task in the JSON file includes:

```json
{
  "id": 1,
  "description": "Buy groceries",
  "status": "todo",
  "createdAt": "2025-06-09T22:00:00",
  "updatedAt": "2025-06-09T22:00:00"
}
```

---

## File Storage

All tasks are stored in a local file named:

```
tasks.json
```

This file is auto-created in the project’s root directory when the program is first run.

---

