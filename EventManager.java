import java.util.ArrayList;

class Event {
    String name;
    int deadline;
    int priority;

    public Event(String name, int deadline, int priority) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
    }
}

class MinHeap {
    private ArrayList<Event> heap;

    public MinHeap() {
        heap = new ArrayList<>();
        heap.add(null); // Placeholder for 1-based index
    }

    public void insert(Event e) {
        heap.add(e);
        heapifyUp(heap.size() - 1);
    }

    public Event removeMin() {
        if (heap.size() <= 1) return null;
        Event min = heap.get(1);
        heap.set(1, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(1);
        return min;
    }

    private void heapifyUp(int index) {
        while (index > 1 && heap.get(index).deadline < heap.get(index / 2).deadline) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    private void heapifyDown(int index) {
        while (index * 2 < heap.size()) {
            int smallest = index;
            int left = index * 2;
            int right = index * 2 + 1;

            if (left < heap.size() && heap.get(left).deadline < heap.get(smallest).deadline) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right).deadline < heap.get(smallest).deadline) {
                smallest = right;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Event temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void display() {
        for (int i = 1; i < heap.size(); i++) {
            Event e = heap.get(i);
            System.out.println("Event: " + e.name + ", Deadline: " + e.deadline + ", Priority: " + e.priority);
        }
    }
}

class EventLog {
    class Node {
        Event event;
        Node next, prev;

        public Node(Event event) {
            this.event = event;
        }
    }

    private Node head, tail;

    public void addEvent(Event e) {
        Node newNode = new Node(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void displayForward() {
        Node temp = head;
        while (temp != null) {
            System.out.println("Completed Event: " + temp.event.name);
            temp = temp.next;
        }
    }

    public void displayBackward() {
        Node temp = tail;
        while (temp != null) {
            System.out.println("Completed Event: " + temp.event.name);
            temp = temp.prev;
        }
    }
}

public class EventManager {
    public static void main(String[] args) {
        MinHeap eventScheduler = new MinHeap();
        EventLog eventLog = new EventLog();

        eventScheduler.insert(new Event("Project Meeting", 2, 1));
        eventScheduler.insert(new Event("Code Review", 1, 2));
        eventScheduler.insert(new Event("Team Lunch", 3, 3));

        System.out.println("Scheduled Events:");
        eventScheduler.display();

        System.out.println("\nCompleting an Event:");
        Event completedEvent = eventScheduler.removeMin();
        eventLog.addEvent(completedEvent);
        System.out.println("Completed: " + completedEvent.name);

        System.out.println("\nRemaining Events:");
        eventScheduler.display();

        System.out.println("\nEvent Log (Forward):");
        eventLog.displayForward();

        System.out.println("\nEvent Log (Backward):");
        eventLog.displayBackward();
    }
}
