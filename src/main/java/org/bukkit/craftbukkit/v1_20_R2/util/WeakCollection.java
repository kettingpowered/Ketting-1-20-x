package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class WeakCollection implements Collection {

    static final Object NO_VALUE = new Object();
    private final Collection collection = new ArrayList();

    public boolean add(Object value) {
        Preconditions.checkArgument(value != null, "Cannot add null value");
        return this.collection.add(new WeakReference(value));
    }

    public boolean addAll(Collection collection) {
        Collection values = this.collection;
        boolean ret = false;

        Object value;

        for (Iterator iterator = collection.iterator(); iterator.hasNext(); ret |= values.add(new WeakReference(value))) {
            value = (Object) iterator.next();
            Preconditions.checkArgument(value != null, "Cannot add null value");
        }

        return ret;
    }

    public void clear() {
        this.collection.clear();
    }

    public boolean contains(Object object) {
        if (object == null) {
            return false;
        } else {
            Iterator iterator = this.iterator();

            while (iterator.hasNext()) {
                Object compare = (Object) iterator.next();

                if (object.equals(compare)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean containsAll(Collection collection) {
        return this.toCollection().containsAll(collection);
    }

    public boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    public Iterator iterator() {
        return new Iterator() {
            Iterator it;
            Object value;

            {
                this.it = WeakCollection.this.collection.iterator();
                this.value = WeakCollection.NO_VALUE;
            }

            public boolean hasNext() {
                Object value = this.value;

                if (value != null && value != WeakCollection.NO_VALUE) {
                    return true;
                } else {
                    Iterator it = this.it;

                    value = null;

                    while (it.hasNext()) {
                        WeakReference ref = (WeakReference) it.next();

                        value = ref.get();
                        if (value != null) {
                            this.value = value;
                            return true;
                        }

                        it.remove();
                    }

                    return false;
                }
            }

            public Object next() throws NoSuchElementException {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("No more elements");
                } else {
                    Object value = this.value;

                    this.value = WeakCollection.NO_VALUE;
                    return value;
                }
            }

            public void remove() throws IllegalStateException {
                Preconditions.checkState(this.value == WeakCollection.NO_VALUE, "No last element");
                this.value = null;
                this.it.remove();
            }
        };
    }

    public boolean remove(Object object) {
        if (object == null) {
            return false;
        } else {
            Iterator it = this.iterator();

            while (it.hasNext()) {
                if (object.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }

            return false;
        }
    }

    public boolean removeAll(Collection collection) {
        Iterator it = this.iterator();
        boolean ret = false;

        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                ret = true;
                it.remove();
            }
        }

        return ret;
    }

    public boolean retainAll(Collection collection) {
        Iterator it = this.iterator();
        boolean ret = false;

        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                ret = true;
                it.remove();
            }
        }

        return ret;
    }

    public int size() {
        int s = 0;

        for (Iterator iterator = this.iterator(); iterator.hasNext(); ++s) {
            Object value = (Object) iterator.next();
        }

        return s;
    }

    public Object[] toArray() {
        return this.toArray(new Object[0]);
    }

    public Object[] toArray(Object[] array) {
        return this.toCollection().toArray(array);
    }

    private Collection toCollection() {
        ArrayList collection = new ArrayList();
        Iterator iterator = this.iterator();

        while (iterator.hasNext()) {
            Object value = (Object) iterator.next();

            collection.add(value);
        }

        return collection;
    }
}
