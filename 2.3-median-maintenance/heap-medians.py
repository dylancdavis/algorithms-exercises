import sys
from heapq import heappush, heappop

input_file = sys.argv[1]

nums = []  # for debugging
medians = []

debug = False


def is_even(n):
    return n % 2 == 0


def add_num(n):
    nums.append(n)

    # Base case: populate heap min first
    if len(heap_smaller_half) == 0 and len(heap_larger_half) == 0:
        heap_smaller_half.append(-n)
        medians.append(n)
        return

    # Once heap min has at least one element,
    # populate appropriate heap
    if n > -heap_smaller_half[0]:
        heappush(heap_larger_half, n)
    else:
        heappush(heap_smaller_half, -n)

    heap_size_diff = abs(len(heap_smaller_half) - len(heap_larger_half))

    # Case: program broke, invariant violated
    if heap_size_diff > 2:
        raise Exception("Invariant violated: heap sizes differ by more than two")

    # Case, odd, append min/max of larger heap
    if heap_size_diff == 1:
        if len(heap_smaller_half) > len(heap_larger_half):
            medians.append(-heap_smaller_half[0])
        else:
            medians.append(heap_larger_half[0])
        return

    # Case, even, but unbalanced (differ by two), rebalance heaps
    if heap_size_diff == 2:
        # Determine which heap to take from, which to move from
        if len(heap_smaller_half) > len(heap_larger_half):
            bigger_heap = heap_smaller_half
            smaller_heap = heap_larger_half
        else:
            bigger_heap = heap_larger_half
            smaller_heap = heap_smaller_half

        num_to_transfer = heappop(bigger_heap)
        # need to invert for transferring between heaps
        heappush(smaller_heap, -num_to_transfer)

        # Check transfer successful
        if not len(heap_smaller_half) == len(heap_larger_half):
            raise Exception(
                "Invariant violated: heap sizes are not the same after rebalancing"
            )

    # Case, even, heaps same size (or just balanced to same size)
    # Default to lesser of median pair
    medians.append(-heap_smaller_half[0])


heap_smaller_half = []  # Note: stores as negative for max heap functionality
heap_larger_half = []


with open(input_file, "r") as f:
    while True:
        next_line = f.readline()
        if next_line == "":
            break
        add_num(int(next_line))

        if debug:
            print("-------")
            print(f"Nums: {nums}")
            print(f"Medians: {medians}")
            print(f"Min Heap: {heap_smaller_half}")
            print(f"Max Heap: {heap_larger_half}")

print(sum(medians))
