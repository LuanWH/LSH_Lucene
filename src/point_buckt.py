def main():
	f = open("data.txt")
	fwrite = open("write.txt","w")
	point_buck = [list() for i in range(100000)]
	print len(point_buck)
	for line in f:
		intArray = line.split()
		bucket = intArray[0]
		for i in range(2,len(intArray)):
			num = intArray[i]
			point_buck[int(num)].append(int(bucket))

	for t in range(len(point_buck)):
		buckets = point_buck[t]
		print buckets
		for item in buckets:
			print item
			fwrite.write(str(item))
			fwrite.write(",")
		fwrite.write("\n")
		"""for bc in buckets:
			fwrite.write(bc)
			fwrite.write(",")
		fwrite.write("\n")
		"""
main()
