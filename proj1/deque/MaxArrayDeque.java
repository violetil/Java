package deque;

import java.util.Comparator;

/** MaxArrayDeque usage like the high order function,
 *  pass the different parameter can implement different result.
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    /** Create a MaxArrayDeque with the given Comparator. */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    /** Return the maximum element in the deque as governed by the initialized Comparator.
     *  If the deque is empty, return null.
     */
    public T max() {
        return max(comparator);
    }

    /** Return the maximum element in the deque as governed by the parameter Comparator.
     *  If the deque is empty, return null.
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            T tmp = get(i);
            int cmp = c.compare(tmp, max);
            if (cmp > 0) {
                max = tmp;
            }
        }
        return max;
    }
}
