#include <vector>
struct fenwick_tree{
    std::vector<long long> t;
};
fenwick_tree init (int nn , fenwick_tree &t);
long long sum (int r , fenwick_tree &t);
void add (int i, int delta , fenwick_tree &t);
long long out_sum (int l, int r , fenwick_tree &t);
void out_arr(fenwick_tree &t);
