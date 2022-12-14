# Suppose I give you 2 dollars. I hold up a coin and say,
# "I will flip this until I get a tails. For every heads, you double your money."
# How much money are you expected to walk away with?

# There's a 1/2 chance of a tail on the first trial, where you earn 2 dollars.
# Then there's a 1/4 chance of a head+tail, where you earn 4 dollars.
# Then 1/8 chance of 8 dollars, 1/16 chance of 16 dollars, and so on.
# Adding the probabilities with their respective values, you get an expected value of
# (1/2)*2 + (1/4)*4 + (1/8)*8 +... = 1 + 1 + 1 +..., which diverges to infinity.
# So are you expected to earn an infinite amount of money?

# This script simulates that theoretical problem by calculating the average over n trials.
# First it calculates the average over 1 trial, then 2, then 4, and so on.
# I noticed that as n gets larger, the averages tends to increase as well, with large variance.
# This could support the mathematical expected value being infinity,
# because as n approaches infinity, so would the expected value.

import random
from math import log2

def run_once(verbose=False, i=None):
    money = 1
    onlyHeads = 1
    while onlyHeads:
        money *= 2 # Really starts at 2
        onlyHeads = random.randint(0,1)
    if verbose:
        print(money) if i == None else print("Trial %d:\t%d" % (i,money))
    return money

def avg_over(n, verbose=False):
    total = sum([run_once(verbose=verbose, i=i) for i in range(n)])
    return total/n

def max_and_avg_over(n, verbose=False):
    trials = [run_once(verbose=verbose, i=i) for i in range(n)]
    total = sum(trials)
    maximum = max(trials)
    return (total/n, maximum)

avg_per_trial = []
for i in range(20):
    trials = 2**i
    avg, maximum = max_and_avg_over(trials)
    print("%d (2^%d) Trials\n\tAverage:\t%f\n\tMax:\t%d (2^%d)" % (trials, i, avg, maximum, log2(maximum)))
    avg_per_trial.append(avg)
print("\nThe ith element is the average over 2^i trials:\n%s" % avg_per_trial)
