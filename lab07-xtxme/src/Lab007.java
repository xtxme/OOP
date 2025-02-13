import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;

public class Lab007{
    public static class MySet<E> implements Set<E> {
        private HashMap<E, Object> map;
        public MySet() {
            map = new HashMap<>();
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

        @Override
        public Object[] toArray() {
            return null;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E element) { // เพิ่มสมาชิกใหม่ในเซต
            if (map.containsKey(element)) {
                return false;
            };
            map.put(element, element);
            return true;
        }

        @Override
        public boolean remove(Object o) { // ลบสมาชิกออกจากเซต
            if(contains(o)){
                map.remove(o);
                return true;
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) { // เช็คว่าสมาชิกทุกตัวในคอลเลกชันมีอยู่ในเซตนี้ไหม
            for (Object element : c) {
                if(!contains(element)){
                    return false;
                }
            }
            return true;
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

        public void GetElement() { //  แสดงผลสมาชิกทั้งหมดในเซต
            for (E element : map.keySet()) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

    }
}

