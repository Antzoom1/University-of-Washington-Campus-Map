/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package poly;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
@SuppressWarnings("JdkObsolete")
public final class RatPolyStack implements Iterable<RatPoly> {

    /**
     * Stack containing the RatPoly objects.
     */
    private final Stack<RatPoly> polys;

    // Abstraction Function:
    // AF(this) = A LIFO stack where the top of this.polys is the top of this,
    // and the bottom of this.polys is the bottom of this (with the elements in between
    // in insertion order, newest closer to the top).
    //
    // RepInvariant:
    // polys != null &&
    // forall i such that (0 <= i < polys.size(), polys.get(i) != null

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (polys != null) : "polys should never be null.";

        for(RatPoly p : polys) {
            assert (p != null) : "polys should never contain a null element.";
        }
    }

    /**
     * @spec.effects Constructs a new RatPolyStack, [].
     */
    public RatPolyStack() {
        polys = new Stack<RatPoly>();
        checkRep();
    }

    /**
     * Returns the number of RatPolys in this RatPolyStack.
     *
     * @return the size of this sequence
     */
    public int size() {
    	return polys.size();
    }

    /**
     * Pushes a RatPoly onto the top of this.
     *
     * @param p the RatPoly to push onto this stack
     * @spec.requires p != null
     * @spec.modifies this
     * @spec.effects this_post = [p]:this
     */
    public void push(RatPoly p) {
        checkRep();
    	polys.push(p);
        checkRep();
    }

    /**
     * Removes and returns the top RatPoly.
     *
     * @return p where this = [p]:S
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = S
     */
    public RatPoly pop() {
       checkRep();
       RatPoly top = polys.pop();
       checkRep();
       return top;
    }

    /**
     * Duplicates the top RatPoly on this.
     *
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = [p, p]:S
     */
    public void dup() {
        checkRep();
        polys.push(polys.peek());
        checkRep();
    }

    /**
     * Swaps the top two elements of this.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S
     */
    public void swap() {
        checkRep();
        RatPoly one = polys.pop();
        RatPoly two = polys.pop();
        polys.push(one);
        polys.push(two);
        checkRep();
    }

    /**
     * Clears the stack.
     *
     * @spec.modifies this
     * @spec.effects this_post = []
     */
    public void clear() {
       checkRep();
       polys.clear();
       checkRep();
    }

    /**
     * Returns the RatPoly that is 'index' elements from the top of the stack.
     *
     * @param index the index of the RatPoly to be retrieved
     * @return if this = S:[p]:T where S.size() = index, then returns p.
     * @spec.requires {@code index >= 0 && index < this.size()}
     */
    public RatPoly getNthFromTop(int index) {
        checkRep();
        Stack<RatPoly> temp = new Stack<RatPoly>();
        // Incorperates the temp stack to hold all the elements before
        // the index element.
        for(int i = 0; i < index; i++) {
            temp.push(polys.pop());
        }
        RatPoly p = polys.peek();
        // pushes all the elements back into the stack
        for(int i = 0; i < index; i++) {
            polys.push(temp.pop());
        }
        checkRep();
        return p;
    }

    /**
     * Pops two elements off of the stack, adds them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
     */
    public void add() {
        checkRep();
        RatPoly one = polys.pop();
        RatPoly two = polys.pop();
        polys.push(one.add(two));
        checkRep();
    }

    /**
     * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
     */
    public void sub() {
        checkRep();
        RatPoly one = polys.pop();
        RatPoly two = polys.pop();
        polys.push(two.sub(one));
        checkRep();
    }

    /**
     * Pops two elements off of the stack, multiplies them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
     */
    public void mul() {
        checkRep();
        RatPoly one = polys.pop();
        RatPoly two = polys.pop();
        polys.push(one.mul(two));
        checkRep();

    }

    /**
     * Divides the next from top poly by the top poly, pops both off the stack, and places the result
     * on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
     */
    public void div() {
        checkRep();
        RatPoly one = polys.pop();
        RatPoly two = polys.pop();
        polys.push(two.div(one));
        checkRep();
    }

    /**
     * Returns an iterator of the elements contained in the stack.
     *
     * @return an iterator of the elements contained in the stack in order from the bottom of the
     * stack to the top of the stack
     */
    @Override
    public Iterator<RatPoly> iterator() {
        return polys.iterator();
    }
}
