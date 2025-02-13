public class Main {
    public static void main(String[] args) {
        Lab007.MySet<Integer> mySet = new Lab007.MySet<>();

        // Test adding elements
        System.out.println("Adding elements to the set:");
        mySet.add(1);
        mySet.add(19);
        mySet.add(2);
        mySet.add(36);
        mySet.add(36);
        mySet.GetElement();

        // Test size and isEmpty
        System.out.println("Size of set: " + mySet.size());
        System.out.println("Is set empty? " + mySet.isEmpty());

        // Test contains
        System.out.println("Contains 2? " + mySet.contains(2));
        System.out.println("Contains 5? " + mySet.contains(5));

        // Test removing elements
        System.out.println("Removing number 36");
        mySet.remove(36);
        mySet.GetElement();

        // Test adding a collection
        System.out.println("Adding a collection {4, 5, 6}:");
        mySet.addAll(java.util.Arrays.asList(4, 5, 6));
        mySet.GetElement();

        // Test removing a collection
        System.out.println("Removing a collection {1, 5}:");
        mySet.removeAll(java.util.Arrays.asList(1, 5));
        mySet.GetElement();

        // Test retainAll
        System.out.println("Retaining elements {4, 6}:");
        mySet.retainAll(java.util.Arrays.asList(4, 6));
        mySet.GetElement();

        // Test clear
        System.out.println("Clearing the set:");
        mySet.clear();
        System.out.println("Is set empty? " + mySet.isEmpty());
        mySet.GetElement();
    }
}