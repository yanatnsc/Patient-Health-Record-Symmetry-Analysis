public class LinkedList {
    Node head;
    Node slow_ptr, fast_ptr, second_half;

    static class Node {
        int metric;
        Node next;

        Node(int metric) {
            this.metric = metric;
            this.next = null;
        }
    }

    //Check if given linked list is symmetric
    boolean isHealthRecordSymmetric(Node head) {
        Node slow_ptr = head;
        Node fast_ptr = head;
        Node prev_of_slow_ptr = head;
        Node midNode = null;
        boolean result = true;

        if (head != null && head.next != null) {
            //Get the middle of the list. Move slow_ptr by 1 and fast_ptr by 2, slow_ptr will have the middle node
            while (fast_ptr != null && fast_ptr.next != null) {
                fast_ptr = fast_ptr.next.next;

                //We need previous of the slow_ptr for linked lists with odd elements
                prev_of_slow_ptr = slow_ptr;
                slow_ptr = slow_ptr.next;
            }

            //fast_ptr would become NULL when there are even elements in the list and not NULL for odd elements. We need to skip the middle node
            // for odd case and store it somewhere so that we can restore the original list
            if (fast_ptr != null) {
                midNode = slow_ptr;
                slow_ptr = slow_ptr.next;
            }

            // Now reverse the second half and compare it with first half
            second_half = slow_ptr;
            prev_of_slow_ptr.next = null; // NULL terminate first half
            reverse(); // Reverse the second half
            result = compareLists(head, second_half); // compare

            // Construct the original list back
            reverse(); // Reverse the second half again

            if (midNode != null) {
                // If there was a midNode (odd size case) which was not part of either first half or second half.
                prev_of_slow_ptr.next = midNode;
                midNode.next = second_half;
            }
            else
                prev_of_slow_ptr.next = second_half;
        }
        return result;
    }

    // Function to reverse the linked list  Note that this function may change the head
    void reverse() {
        Node prev = null;
        Node current = second_half;
        Node next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        second_half = prev;
    }

    // Helper method to compare if two input lists have same data
    boolean compareLists(Node head1, Node head2) {
        Node node1 = head1;
        Node node2 = head2;

        while (node1 != null && node2 != null) {
            if (node1.metric == node2.metric) {
                node1 = node1.next;
                node2 = node2.next;
            }
            else
                return false;
        }

        // When reaching the end of the lists and both are empty return 1
        if (node1 == null && node2 == null) {
            return true;
        }

        // Will reach here when one is NULL and other is not
        return false;
    }

    // Add nodes to the list
    public void add(int data) {
        // Create a new node with the given data
        Node newNode = new Node(data);

        // If the list is empty, set the new node as the head of the list
        if (head == null) {
            head = newNode;
        } else {
            // Otherwise, find the last node in the list
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }

            // Append the new node to the end of the list
            current.next = newNode;
        }
    }

    // A utility function to print a given linked list
    void printList(Node ptr) {
        while (ptr != null) {
            System.out.print(ptr.metric + "->");
            ptr = ptr.next;
        }
        System.out.println("NULL");
    }

    // Driver program to test the above functions
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        int array1[] = {1, 2, 3, 4, 5, 4, 3, 2, 1};
        int array2[] = {1, 2, 3, 4, 4, 3, 2, 1};
        int array3[] = {1, 2, 3, 4, 5, 6, 7};
        int array4[] = {};

        int[][] arraysToTest = {array1, array2, array3, array4};

        for(int[] array : arraysToTest) {
            list.head = null;

            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }

            System.out.print("Linked List: ");
            list.printList(list.head);

            if (list.isHealthRecordSymmetric(list.head)) {
                System.out.println("is symmetric");
                System.out.println();
            }
            else {
                System.out.println("is not symmetric");
                System.out.println();
            }
        }
    }
}
