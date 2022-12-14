"""
Collection of probability formulas, including expected value and variance
"""

from math import factorial, prod, exp

def choose(n, k):
    def c(n, k):
        return prod(range(k+1, n+1))//factorial(n-k)
    return c(n, max(k, n-k))

def sort(x1, x2=None):
    if x2 == None:
        return (x1, x1)
    return (min(x1, x2), max(x1, x2))

def p_bin(n, p, k):
    return choose(n, k) * (p**k) * ((1-p)**(n-k)) if k <= n else 0

def p_bin_range(n, p, k1, k2=None):
    """Find probability that x∈[k1,k2], where x is a random integer from 0 to n and P(success)=p"""
    lower, upper = sort(k1, k2)
    options = upper-lower+1
    total = n+1

    # If the options are the majority of the total, it is more efficient to calculate 1 - (non-options)
    if options/total > 0.5:
        return 1 - (sum([p_bin(n, p, i) for i in range(0, k1)]) + sum([p_bin(n, p, i) for i in range(k2+1, n+1)]))
    
    return sum([p_bin(n, p, i) for i in range(lower, upper+1)])

def p_psn(a,x):
    return ((a**x)*(exp(-a)))/factorial(x)

def p_psn_range(a,x1,x2=None):
    lower, upper = sort(x1, x2)
    return sum([p_psn(a, i) for i in range(lower, upper+1)])

def p_geo(p, x):
    return ((1-p)**(x-1))*p

def ev_bin_naive(n, p):
    outcomes = range(n+1)
    probs = [p_bin(n, p, i) for i in outcomes]
    return sum([o*p for o,p in zip(outcomes, probs)])

def ev_bin(n, p):
    return n*p

def var_bin(n, p):
    return n*p*(1-p)

def ev_psn(a):
    return a

def var_psn(a):
    return a

def ev_geo(p):
    return 1/p

def print_stats(P, ev, var):
    if not P == None:
        print("P\t%s" % P)
    print("EV\t%s" % ev)
    print("Var\t%s" % var)
    print("sd\t%s" % var**(1/2))

def bin_stats(n, p, k1=None, k2=None):
    P = None if k1 == None else p_bin_range(n,p,k1,k2)
    print_stats(P, ev_bin(n, p), var_bin(n, p))

def psn_stats(a,x1=None,x2=None):
    P = None if x1 == None else p_psn_range(a,x1,x2)
    print_stats(P, ev_psn(a), var_psn(a))
    