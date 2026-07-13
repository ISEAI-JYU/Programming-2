# Version Control

> [!IMPORTANT]
>
> This chapter assumes that you have used Git version control before.
>
> If you have not used Git previously or need a refresher, first review the Git materials from your earlier programming studies.
>
> At this stage, we do not yet need a remote repository.

Similarly, using Git from the command line assumes that you are already somewhat familiar with command-line tools.
If necessary, revisit the command-line materials from earlier courses.

At this point, it is a good idea to begin using version control.
We will use Git* which is one of the most widely used version-control tools in software development.
After this section, you should create a separate Git commit for each tutorial exercise, describing the changes made during that exercise.

Git can be used through many different interfaces—including one built into IDEA—but we will use the command line because it is a common approach that works the same way in practically every environment.

Let's begin by creating a Git repository for the project.
Open a terminal and navigate to your project's root directory.
The root directory is the folder that contains the `src` directory and the `pom.xml` file.
Initialize the Git repository with `git init`.

<asciinema src="images/git-init.cast" rows="4" poster="npt:10"></asciinema>

You should receive a message indicating that an empty Git repository has been created.
The path shown in the message will naturally be different on your own computer.

Before creating the first commit, we must tell Git which files should be included.
To begin, simply add all files in the project directory
`git add .`:

<asciinema src="images/git-add.cast" rows="2" poster="npt:10"></asciinema>


This adds all files in the current directory and its subdirectories to the next commit.
Note that the command itself usually produces no output.

Let's verify which files will be included by running 
`git status:

<asciinema src="images/git-status.cast" rows="19" poster="npt:10"></asciinema>


You will see a list of files that Git is tracking and which will be included in the next commit.
Let's briefly examine the contents.
You should see familiar files from the previous chapters:
`pom.xml`, Java source files (`.java`) and FXML view files (`.fxml`).
The `.idea` directory contains settings used by IntelliJ IDEA.

You will also see a `.gitignore` file.
This file was included in the project template.
It tells Git which files should **never** be added to commits.
This ensures that compiled `.class` files and IDEA-specific configuration files do not end up under version control.
The `.gitignore` file can and should be modified if you later decide that additional files should be excluded from version control.

Now we can create our first commit, which stores the current state of the project in Git as a kind of snapshot.
When creating a commit, you provide a descriptive message explaining the changes being recorded.
Typical messages for the first commit are
"Initial commit"
or 
"Project initialization"
Create the commit using: `git commit`

<asciinema src="images/git-commit.cast" rows="14" poster="npt:10"></asciinema>

Git will display a list of files whose current state has been successfully stored in the repository.

From this point onward, create a new commit after each exercise and describe the changes made during that exercise.
You may also create multiple commits for a single exercise if you wish.