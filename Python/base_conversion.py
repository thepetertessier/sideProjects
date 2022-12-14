def dec2bin(num):
    """Convert a decimal number to binary"""
    b = []
    while num > 0:
        b.insert(0, num % 2)
        num = num // 2
    return b

def dec2any(num, base, log=False):
    """Convert a decimal number to any base"""
    output = []
    while num > 0:
        output.insert(0, num % base)
        num = num // base
    if log:
        print("".join([str(n) for n in output]))
    return output

def int2list(num):
    return [int(x) for x in list(str(num))]

def any2dec(num, base):
    if isinstance(num, int):
        num = int2list(num)
    output = 0
    power = 0
    while len(num) > 0:
        output += num[-1] * base**power
        num = num[:-1]
        power += 1
    return output

def any2any(num, base1, base2):
    """Convert number of base1 to number of base2"""
    return dec2any(any2dec(num, base1), base2)

def bin2hex(b):
    """Convert a binary list to hexidecimal"""

    output = []
    while len(b) > 0:
        four = b[:4]
        while len(four) < 4:
            four.insert(0, 0)
    # unfinished


# dec2any(42, 2, log=True)
print(any2any(10001,2,4))