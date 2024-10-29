import sys

input_file = sys.argv[1]

# both bounds will be treated as inclusive
upper_bound = 10000
lower_bound = -upper_bound

# Parse input
with open(input_file) as f:
    nums = [int(line) for line in f]

num_set = set(nums)
nums_to_find = set(range(lower_bound, upper_bound + 1))
total_nums = len(nums_to_find)

for index, n in enumerate(num_set):  # 1M
    if index % 1000 == 0:
        print(f"Checking index: {index} ")
    found_nums = set()
    for k in nums_to_find:  # 20k, but decreasing as more found. in theory.
        diff = k - n
        if not diff == k and diff in num_set:
            found_nums.add(k)
            print(f"Found {k}")
    for k in found_nums:
        nums_to_find.remove(k)
        print(f"Nums left: {len(nums_to_find)}")

nums_found = total_nums - len(nums_to_find)
print(f"Nums found: {nums_found}")
