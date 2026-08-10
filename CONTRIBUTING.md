# Contributing and Development

Welcome to contributing to the Programming 2 course material! We greatly appreciate all improvement suggestions, whether they are small typo fixes or larger content changes.

## License

Please note that any changes made to this repository are published under the [CC BY-SA 4.0](LICENSE) license.
By submitting changes to this repository, you agree that your contributions will be published under the terms of the CC BY-SA 4.0 license.

The license does not apply to issues submitted to this repository.

## How Can You Help?

There are many ways to contribute:

1. **Report an issue:** If you notice an error but do not have time to fix it yourself, create a new Issue.
2. **Small fixes:** Typos and minor clarifications are easiest to make directly through GitHub's web interface.
   - **Editing files in GitHub:** See GitHub's [documentation](https://docs.github.com/en/repositories/working-with-files/managing-files/editing-files) for editing files directly in a repository.
- **Larger changes:** If you would like to add new examples or chapters, we recommend setting up a local development environment.

---

## Setting Up the Development Environment

If you want to make larger changes and preview them locally, follow these steps.

### 1. Authentication and Cloning

We recommend using an [SSH key](https://docs.github.com/en/authentication/connecting-to-github-with-ssh) for authentication.

```bash
git clone git@github.com:ohj-perus-jy/ohj2.git
cd ohj2
```

### 2. Installing the Tools

The material is built using **mdBook**.
The recommended approach is to use the included DevContainer. It uses the prebuilt GHCR image:
`ghcr.io/ohj-perus-jy/ohj-mdbook-tooling:main`
which already contains mdBook and all required extensions.

If you do not use the DevContainer, you will need [Rust and Cargo](https://www.rust-lang.org/tools/install) installed. You can then install the mdBook tooling using the fallback script:

```bash
bash ./update-mdbook.sh
```

The fallback installation compiles some tools from source, so the first run may take a while.

### 3. Local Preview

Start the development server from the project root:

```bash
bash ./start.sh
```

This opens the material in your browser (by default at `localhost:3000`) and automatically refreshes the view when you save changes.

You can test only the build without running the development server:

```bash
mdbook build
```

--- 

## Workflow

When proposing changes, follow this process:

1. **Create a New Branch**
    ```bash
    git switch -c fix-topic
    ```

2. **Make Your Changes:**
    Edit the Markdown files under the `src` directory. Follow the project's style guide and existing conventions.

3. **Commit and Push**

    Try to write clear commit messages. If your change is related to an open issue, you may reference it in the commit message (for example: `Fixed typo #123`).

    ```bash
    git add .
    git commit -m "Descriptive commit message"
    git push -u origin fix-topic
    ```

4. **Create a Pull Request (PR)**

    Open the project page on GitHub and create a new Pull Request from your branch.
    The instructors will review your proposal and provide feedback if necessary.

Thank you for helping improve the course material!