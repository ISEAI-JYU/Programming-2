Implement a class called `Door` that models a door which can be either locked or open.

Attributes:
- `private boolean locked`
- `private String keyCode`

The constructor receives the door's key code as a parameter:
`Door(String keyCode)`

The constructor must set the key code and initially set the door to the locked state.

Methods:

- `boolean open(String code)`: opens the door only if the code is correct **and** the door is locked. Returns `true` if the door was opened; otherwise returns `false`.

- `boolean lock()`: locks the door only if it is open. Returns `true` if locking was successful; otherwise returns `false`.

- `boolean changeCode(String oldCode, String newCode)`: changes the key code to a new one if the old code is correct **and** the door is open. The new code cannot be an empty string. Returns `true` if the change was successful; otherwise returns `false`.

- `String getStatus()`: returns either `"Door is locked"` or `"Door is open"`.

Only `getStatus()` may produce output. The other methods must not print anything.

Write a main program that:

- creates a door
- locks the door
- attempts to open the door with an incorrect code
- opens the door with the correct code
- attempts to open an already open door
- attempts to lock an already locked door
- attempts to change the code while the door is locked
- attempts to change the code using an incorrect old code
- changes the code using the correct old code
- prints the status of the door

---