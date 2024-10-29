import sys

input_file = sys.argv[1]

nums = []
medians = []

debug = False


def is_even(n):
    return n % 2 == 0


def add_num(n):
    if len(nums) == 0:
        nums.append(n)
        return

    index = 0
    while (index < len(nums)) and (nums[index] < n):
        index += 1

    nums.insert(index, n)


def get_median():
    midpoint = len(nums) // 2

    if is_even(len(nums)):
        midpoint -= 1

    return nums[midpoint]


with open(input_file, "r") as f:
    while True:
        next_line = f.readline()
        if next_line == "":
            break
        next_num = int(next_line)
        add_num(next_num)
        next_median = get_median()
        medians.append(next_median)
        if debug:
            print("-------")
            print(f"Nums: {nums}")
            print(f"Medians: {medians}")

print(sum(medians))
