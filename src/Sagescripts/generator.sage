Q.<i,j,k> = QuaternionAlgebra(SR, -1, -1) #SR = symbolic Ring, -1, -1???
s = (sqrt(5)-1)/2
t = (sqrt(5)+1)/2
w =(-1+i+j+k)/2
qi = (i+s*j+t*k)/2


x = qi*w
rQuat = set([qi,w,x]) # Set of quaternions
newQuat = list([x])
z = 0
while(len(newQuat) > 0 and z < 70):
	print('z=',z)
	z = z+1
	x = newQuat.pop()
	rQuat.add(x)
	for y in rQuat:
		result = x*y
		if(result not in newQuat and result not in rQuat):
			newQuat.append(result)
			print result
			print ('#new: ',len(newQuat))
