# 📊 Binary Search Tree (BST) & AVL Tree Demo (Java)

This project is a **Java-based GUI application** demonstrating the functionality and performance of **Binary Search Trees (BST)** and **AVL Trees**. The program visualizes tree structure, performs standard operations, and benchmarks removal speed. It simulates a **Car Market** scenario with auto-generated `Car` objects as data elements.

## 🚀 Features

- ✅ **Visual BST and AVL tree structure display**
- 📈 **Performance benchmarking** of set operations (especially `remove`)
- 🧪 **Manual testing and data entry**
- ⚙️ **Tree balancing, iteration, and random generation**
- 🎛️ **GUI built with JavaFX for interactive control**
- 📁 **Customizable input from files or generated datasets**

---

## 📂 Project Structure

| Folder/File           | Description |
|-----------------------|-------------|
| `utils/`              | Set interfaces and AVL/BST implementations (`AvlSet`, `BstSet`, etc.) |
| `demo/`               | Car generation logic, performance tools, entry point logic |
| `gui/`                | JavaFX GUI and validation (`MainWindow.java`, `Panels.java`, etc.) |
| `Car.java`            | Core model representing a car object |
| `CarsGenerator.java`  | Generates `Car` objects and handles randomized shuffling |
| `MainWindow.java`     | JavaFX interface with live tree rendering and controls |
| `Parsable*Set.java`   | Interfaces that load data from files and parse them into trees |
| `ManualTest.java`     | Manual, console-based tree testing interface |
| `ValidationException.java` | Custom exception for form validation errors |

---

## 💻 How to Run

### Requirements:
- Java 11+
- JavaFX SDK
- IntelliJ IDEA / Eclipse or any IDE with JavaFX support

### Steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/tatendamawango/binary-search-tree-java.git
   cd binary-search-tree-java
   ```

2. Open in your preferred IDE and ensure JavaFX is set up.

3. Run the `MainWindow.java` class from the `gui` package.

---

## 🎮 GUI Preview

The GUI window allows:
- 👁️ Live tree visualization
- 📥 Load dataset from file
- 🔄 Add/remove random cars
- 🧮 Tree balancing (AVL)
- 🧪 Speed testing of operations

---

## 📈 Sample Benchmark Code

```java
Object[] cars = carsSet.toArray();
Car carToRemove = (Car) cars[cars.length - 1];
long start = System.nanoTime();
carsSet.remove(carToRemove);
System.out.println("Removal Time: " + (System.nanoTime() - start));
```

---

## 📚 Concepts Demonstrated

- Binary Search Tree (BST) & AVL Tree balancing
- Tree traversal & visualization
- Generics, JavaFX, Streams
- Exception handling
- File-based data loading
- JavaFX-based UI and interaction
