#include <vector>
#include "fenwick_tree.h"
fenwick_tree init (int n , fenwick_tree &t)
{
    t.t.assign (n + 1, 0);
    return t;
}

long long sum (int r , fenwick_tree &t)
{
    int result = 0;
    for (; r >= 0; r = (r & (r+1)) - 1)
        result += t.t[r];
    return result;
}

void add (int i, int delta , fenwick_tree &t)
{
    for (; i < t.t.size(); i = (i | (i   +1)))
        t.t[i] += delta;
}

long long out_sum (int l, int r , fenwick_tree &t)
{
    return sum (r , t) - sum (l-1 , t);
}