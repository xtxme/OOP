import java.util.*;

public class Lab08 {

    public static class MySet<E> implements Set<E> { //เอา Set มาสร้างใหม่โดยใช้ HashMap
        private final HashMap<E, Object> map;
        private static final Object PRESENT = new Object(); // กำหนดค่าคงที่ PRESENT เป็นค่าเริ่มต้นของ value ใน HashMap

        public MySet() {
            map = new HashMap<>(); //สร้างออบเจกต์ HashMap ใหม่ และกำหนดให้ตัวแปร map อ้างอิงถึงออบเจกต์
        }

        @Override
        public int size() { //คืนค่าจำนวนสมาชิกในเซต
            return map.size();
        }

        @Override
        public boolean isEmpty() { //ตรวจสอบว่าเซตว่างหรือไหม
            return map.isEmpty();
        }

        @Override
        public boolean contains(Object o) { //เช็คว่ามีสมาชิกตัวนี้อยู่ในเซตหรือไหม
            return map.containsKey(o);
        }

        @Override
        public Iterator<E> iterator() { // คืนค่า Iterator เป็นตัวช่วยสำหรับวนลูปข้อมูลในเซต
            return map.keySet().iterator();
        }
        // keySet() คืนค่า Set ที่ประกอบด้วย คีย์ทั้งหมด (keys) ใน HashMap
            //- สามารถเข้าถึงคีย์ทั้งหมดใน HashMap
            //- สามารถวนลูปเพื่อประมวลผลคีย์แต่ละตัวได้
            //- ใช้ตรวจสอบว่าคีย์นั้นมีอยู่ใน HashMap ไหม

        @Override
        public Object[] toArray() { // คืนค่าทุกตัวใน Set ในรูปแบบ อาเรย์
            return map.keySet().toArray(); // toArray() จะถูกเรียกเพื่อแปลงสมาชิกทั้งหมดเป็น อาเรย์ของ Object
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return map.keySet().toArray(a); //จะทำการแปลงค่าทั้งหมดในเซตเป็นอาเรย์ชนิด T[]
        }

        @Override
        public boolean add(E element) { // เพิ่มสมาชิกใหม่ในเซต
            if (map.containsKey(element)) {
                return false;
            }
            map.put(element, PRESENT);
            return true;
        }

        @Override
        public boolean remove(Object o) { // ลบสมาชิกออกจากเซต
            return map.remove(o) != null;
        }

        @Override
        public boolean containsAll(Collection<?> c) { // เช็คว่าสมาชิกทุกตัวในคอลเลกชันมีอยู่ในเซตนี้ไหม
            return map.keySet().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) { // เพิ่มสมาชิกทุกตัวจากคอลเลกชันเข้าไปอยู่ในเซต
            boolean modified = false;
            for (E element : c) {
                if (add(element)) {
                    modified = true;
                }
            }
            return modified;
        }

        @Override
        public boolean retainAll(Collection<?> c) { //เก็บเอาไว้เฉพาะสมาชิกที่เลือก
            boolean modified = false;
            Iterator<E> it = iterator();
            while (it.hasNext()) {
                if (!c.contains(it.next())) {
                    it.remove();
                    modified = true;
                }
            }
            return modified;
        }

        @Override
        public boolean removeAll(Collection<?> c) { // ลบสมาชิกตัวที่กำหนดในเซตออก
            boolean modified = false;
            for (Object element : c) {
                if (remove(element)) {
                    modified = true;
                }
            }
            return modified;
        }

        @Override
        public void clear() { // ลบสมาชิกทุกตัวในเซตออก
            map.clear();
        }

        @Override
        public boolean equals(Object o) { // ตรวจสอบว่า Set เท่ากับ Set อื่นหรือไม่ โดยเปรียบเทียบค่าของ map.keySet()
            if (this == o) return true;
            if (!(o instanceof Set)) return false;
            Set<?> other = (Set<?>) o;
            return map.keySet().equals(other);
        }

        @Override
        public int hashCode() { //สร้างค่า hashCode ของ Set โดยรวมค่า hashCode ของทุกค่าภายใน Set
            return map.keySet().stream().map(Object::hashCode).sorted().reduce(0, Integer::sum);
        }

        @Override
        public String toString() {
            return map.keySet().toString();
        }

        public void displayElements() {
            System.out.println(map.keySet());
        }
    }

    public static Comparator<String> shortLexComparator() {
        return Comparator.comparingInt(String::length)
                .thenComparing(String::compareToIgnoreCase);
    }

    // Comparator for sets
    public static <E> Comparator<Set<E>> setComparator(Comparator<E> elementComparator) {
        return (set1, set2) -> {
            Iterator<E> it1 = set1.iterator();
            Iterator<E> it2 = set2.iterator();
            while (it1.hasNext() && it2.hasNext()) {
                int cmp = elementComparator.compare(it1.next(), it2.next());
                if (cmp != 0) {
                    return cmp;
                }
            }
            return Integer.compare(set1.size(), set2.size());
        };
    }


    // Main method for testing
    public static void main(String[] args) {
        // Test shortLexComparator
        List<String> animals = Arrays.asList("cat", "Elephant", "dog", "rabbit", "ant");
        animals.sort(shortLexComparator());
        System.out.println("Animals sorted by shortLex: " + animals);

        // Create sets and test equality/interoperability
        MySet<String> testSet1 = new MySet<>();
        testSet1.add("cat");
        testSet1.add("dog");
        testSet1.add("rabbit");
        testSet1.add("Elephant");

        Set<String> javaSet = new HashSet<>();
        javaSet.add("cat");
        javaSet.add("dog");
        javaSet.add("rabbit");

        MySet<String> testSet9 = new MySet<>();
        testSet1.add("cat");
        testSet1.add("dog");
        testSet1.add("rabbit");
        testSet1.add("Elephant");

        MySet<String> testSet10 = new MySet<>();
        testSet1.add("cat");
        testSet1.add("dog");
        testSet1.add("rabbit");
        testSet1.add("Elephant");

        System.out.println("testSet1 equals HashSet: " + testSet1.equals(javaSet));
        System.out.println("HashSet equals MySet: " + javaSet.equals(testSet1));

        // Test setComparator
        MySet<String> mySet2 = new MySet<>();
        mySet2.add("rabbit");
        mySet2.add("ant");

        List<Set<String>> sets = Arrays.asList(testSet1, mySet2, javaSet);
        Comparator<Set<String>> setCmp = setComparator(shortLexComparator());
        sets.sort(setCmp);

        System.out.println("Sets sorted by shortLexComparator: ");
        sets.forEach(System.out::println);

        System.out.println(testSet10.hashCode());
        System.out.println(testSet9.hashCode());
    }
}
