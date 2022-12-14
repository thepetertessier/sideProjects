# The following code was used to crack the impossible chessboard problem, explained here:
# https://www.youtube.com/watch?v=wTJI_WuZSwE

# The get_position() function maps an arrangement of coins (64 heads and tails)
# to a single position on the chessboard (int from 0 to 63), and this function
# must have the property that for any arrangement of coins, one can always flip
# one coin such that get_position(new arrangment) equals any number from 0 to 63.

# After I found such a get_position() function using recursion, I wrote more code
# and tests to solve the problem: Which coin would you have to flip in order for
# get_position(new arrangement) to equal the position you desire? I finally discovered
# that you must flip get_position("arrangment if coin in desired position were flipped").

# Scroll down to line 232 to see a sample situation, and run the program to see it play out.

import numpy as np
import random


def hmg(b1, b2):
    """
    Calculate hamming distance between two binary lists
    """
    b1, b2 = (np.array(b) for b in (b1, b2))
    diff = (b1 + b2) % 2
    return sum(diff)


def is_power_of_two(x):
    # Credit to Danish Raza for this algorithm
    # First x in the below expression is for the case when x is 0
    return (x and (not(x & (x - 1))))


def bin_lst_to_dec(bin_lst):
    """
    Converts bin_lst to decimal number. For example, [1,0,1,0] -> 10
    """
    dec = 0
    for i, x in enumerate(bin_lst):
        dec += x * (2 ** (len(bin_lst)-1 - i))
    return dec


def listify(coins):
    return list(map(int,str(coins))) if isinstance(coins, (int, str, np.int32)) else list(coins)


def get_halves(coins, position=[], verbose=False):
    """
    Recursively determine which half the coins point to

    :param coins: np array of 0's and 1's that mark heads and tails
    :param position: list of 0's and 1's 
        defines key's position from whole board until right before this call
    :return: list of 0's and 1's
        defines key's position from whole board until right after this call
    """
    if verbose:
        print('\nStep %d\n------' % (len(position) + 1))
        print('coins: %s' % coins)
        
    # I will walk through an example with 7 digits
    # split = (7-1)/2 = 3
    split = (len(coins) - 1) // 2

    # How many of first 4 digits equal 1? If even, key is in first half. Else, in second half.
    is_in_second_half = sum(coins[:split + 1] == 1) % 2
    position.append(is_in_second_half)
    if verbose:
        print('position: %s' % position)

    # Terminate if we are on the last half
    if len(coins) == 1:
        return position
    else:
        # Get bitwise difference between first 3 and last 3
        b1 = coins[:split]
        b2 = coins[split * -1:]
        diff = (b1 + b2) % 2

        # Recursively repeat
        return get_halves(diff, position, verbose=verbose)


def get_position(coins, verbose=False):
    """
    :param coins: list of 0's and 1's that mark heads and tails
    :return: position it maps to
    """
    coins = listify(coins)
    if not is_power_of_two(len(coins)):
        print("Failure: coin arrangment '%s' does not have 2^k digits" % coins)
        return
    position_lst = get_halves(np.array(coins[:-1]), [], verbose=verbose)
    if verbose:
        print('final position (list): %s' % position_lst)
    position = bin_lst_to_dec(position_lst)
    return position


def gen_bin(digits, bin_lsts):
    if len(bin_lsts[0]) == digits:
        return bin_lsts
    else:
        new_lst = []
        for bin_lst in bin_lsts:
            for i in range(2):
                new_lst.append(bin_lst + [i])
        return gen_bin(digits, new_lst)


def get_all_arrangements(tiles):
    """
    :param tiles: number of tiles (e.g., 4, 8, 16, 64)
    """
    assert tiles >= 0, "'tiles' argument '%s' must be non-negative integer" % tiles
    arrangements = gen_bin(tiles, [[0],[1]])
    return arrangements


def get_coins2pos(tiles):
    coins2pos = {i:[] for i in range(tiles)}
    for bin_lst in get_all_arrangements(tiles):
        coins2pos[get_position(bin_lst)].append(bin_lst)
    return coins2pos    


def is_valid(tiles):
    coins2pos = get_coins2pos(tiles)
    for i in coins2pos:
        for b1 in coins2pos[i]:
            for b2 in coins2pos[i]:
                if hmg(b1,b2) == 2:
                    print("'%s' and '%s' are 2 apart" % (b1, b2))
                    return False
    return True


def get_adjacents(bin_lst):
    """
    Returns a list 'adjacents' such that if you flip the ith coin (starting at the end),\
    your new arrangement maps to adjacents[i]
    """
    b = np.array(listify(bin_lst))
    adjacents = [None for i in bin_lst]
    for i, digit in enumerate(b):
        b[i] = (digit + 1) % 2
        adjacents[i] = get_position(b)
        b[i] = (b[i] + 1) % 2
    return adjacents


def flip(arr, c):
    """
    Returns position p2 after previously get_position(arr) = p1 and flipping coin in position c
    """
    arr = listify(arr)
    arr[-c-1] = (arr[-c-1] + 1) % 2
    return get_position(arr)


def rand_coins(tiles):
    return [random.randint(0,1) for i in range(tiles)]


def is_valid_adjacents(bin_lst):
    return set(get_adjacents(bin_lst)) == set(range(len(bin_lst)))


def demo(tiles):
    b_rand = rand_coins(tiles)
    print("Arrangement: %s" % b_rand)
    print("        Key: %s" % get_adjacents(b_rand))


def test_rand(trials, tiles):
    for i in range(trials):
        b_rand = rand_coins(tiles)
        print("Testing: %s" % b_rand)
        if is_valid_adjacents(b_rand):
            print("Adjacents are valid")
        else:
            print("Invalid adjacents")
            break


def test_rand_flip(trials, tiles):
    for i in range(trials):
        b_rand = rand_coins(tiles)
        print("Testing: %s" % b_rand)
        for i in range(tiles):
            if flip(b_rand, flip(b_rand, i)) != i:
                print("Flip theorem is invalid")
                return
        print("Flip theorem is valid")


def gen_solution_matrix(coins):
    for i in range(coins):
        seed = [0]*coins
        seed[-i-1] = 1
        print(get_adjacents(seed))

def flip_coin(arrangement, coin):
    arr_copy = arrangement[::-1]
    arr_copy[-coin-1] = 0 if arr_copy[-coin-1] else 1
    return arr_copy


# Testing

# coins2pos = get_coins2pos(4)
# coins2pos_matrix = [coins2pos[i] for i in coins2pos]
# mat = [["".join([str(digit) for digit in lst]) for lst in column] for column in coins2pos_matrix]
# print(np.array(mat))

# for b in get_all_arrangements(8):
#     print("\n%s" % b)
#     print(get_adjacents(b))

# gen_solution_matrix(4)
# print(get_position([1,1,0,0,1,0,0,1,1,1,0,0,1,0,0,1]))

# NEW DISCOVERY:
# flip(arr1, flip(arr1, p)) = p
# Therefore, to find c such that flip(arr1, c) = alpha, set c = flip(arr1, alpha)

# test_rand_flip(100,64)


# Sample situation:
# Suppose our chessboard has the following arrangement
arrangement = [0, 0, 0, 0, 1, 1, 0, 0,
               0, 1, 0, 0, 0, 1, 0, 0,
               1, 1, 0, 1, 0, 1, 0, 0,
               0, 0, 1, 0, 0, 0, 1, 1,
               0, 1, 0, 1, 0, 1, 1, 1,
               0, 1, 1, 0, 1, 1, 0, 1,
               0, 0, 0, 1, 0, 0, 1, 0,
               0, 0, 1, 0, 0, 0, 0, 0]
print("Arrangement: %s" % arrangement)

# And the key is in tile #23
key_index = 23
print("Key index: %d" % key_index)

# To know which coin to flip in order for get_position(new arrangement) = 23,
# we must call get_position() on the arrangement where coin #23 is flipped
arrangement_with_23_flipped = flip_coin(arrangement, coin=key_index)
coin_we_should_flip = get_position(arrangement_with_23_flipped)

# Now we flip that coin
print("Now flipping coin #%d..." % coin_we_should_flip)
new_arrangement = flip_coin(arrangement, coin=coin_we_should_flip)

# Person 2 comes in and checks under the result of get_position(new_arrangement),
# which should be where the key is
key_index_2 = get_position(new_arrangement)
print("Person 2 checks under tile #%d, which should be the key index!" % key_index_2)
