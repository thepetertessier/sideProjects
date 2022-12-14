from math import pi, exp, factorial, floor, log10

def gauss(u,o,x):
    """Probability density function of Gaussian (normal) distribution"""
    return (1/(2*pi*(o**2))**(1/2))*exp(-1*((x-u)**2)/(2*(o**2)))

def cdf(x,u=0,o=1,e=10**-10,debug=False,return_steps=False):
    """
    Area under normal distribution from negative infinity to x

    :param x: upper bound
    :param u: mean
    :param o: standard deviation
    :param e: error bound
    :param debug: print calculation steps
    :param return_steps: return tuple (result, number of calculation steps)

    :return: float from 0 to 1
    """
    # Normalize x and e
    x = (x-u)/o
    e = abs(e)

    # Approximate outliers to avoid excessive calculation
    if x <= -5:
        return (0,0) if return_steps else 0
    if x >= 5:
        return (1,0) if return_steps else 1

    # ith element of Taylor Series
    def a(i):
        coeff = (1/(2*pi)**(1/2))
        return (coeff*((-1)**i)*(x**(2*i+1))) / (factorial(i)*(2**i)*(2*i+1))
        
    # Sum a_i until a_(i+1) <= error
    result = 1/2
    i = 0
    while True:
        a_i = a(i)
        if abs(a_i) <= e:
            break # before adding that final value
        if debug:
            print("a_%d = %s" % (i, a_i))
        result += a_i
        i += 1
    
    # Round to same decimal as error
    num_of_dec_places = floor(log10(e))
    result = round(result, -1*num_of_dec_places)

    if return_steps:
        return (result,i)

    return result

def cdf_range(x1,x2=None,u=0,o=1,e=10**-10,debug=False,print_num_of_steps=False):
    """
    Area under normal distribution from x1 to x2

    :param x1: lower bound
    :param x2: upper bound
    :param u: mean
    :param o: standard deviation
    :param e: error bound
    :param debug: print calculation steps
    :param print_num_of_steps: print total number of calculation steps
    """
    if x2 == None:
        if debug or print_num_of_steps:
            result = cdf(x1,u,o,e,debug,return_steps=True)
            if print_num_of_steps:
                print("Steps: %d" % (result[1]))
            return result[0]
        return cdf(x1,u,o,e,debug)

    if debug or print_num_of_steps:
        top = cdf(max(x1,x2),u,o,e,debug,return_steps=True)
        bottom = cdf(min(x1,x2),u,o,e,debug,return_steps=True)
        if print_num_of_steps:
            print("Steps: %d" % (top[1]+bottom[1]))
        return top[0] - bottom[0] 

    return cdf(max(x1,x2),u,o,e) - cdf(min(x1,x2),u,o,e)

u = 0
o = 1
k = 1.96
print(cdf_range(-k,k,u,o,debug=True,print_num_of_steps=True))
