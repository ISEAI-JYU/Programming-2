Create a new Maven project. Set the package name to `fi.jyu.username` (replace `username` with your JYU username or another username of your choice).
Name the main class `Dependencies`. Add the following code to it:

```java,ignore
void main() {
    JSONObject json = new JSONObject();
    json.put("name", "Maija");
    json.put("age", 25);
    IO.println(json.getString("name"));
    IO.println(json.getInt("age"));
}
```

Now add a dependency to the `json` artifact in the `pom.xml` file. Find this library on Maven Central and copy the XML dependency definition into your `pom.xml` file.
Also add the required `import` statement at the beginning of the class.

Compile and run the program, and make sure that it prints the expected data.