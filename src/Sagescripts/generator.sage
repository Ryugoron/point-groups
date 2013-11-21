Q.<i,j,k> = QuaternionAlgebra(SR, -1, -1) #SR = symbolic Ring, -1, -1???
s = (sqrt(5)-1)/2
t = (sqrt(5)+1)/2
w =(-1+i+j+k)/2
qi = (i+s*j+t*k)/2


x = qi*w
rQuat = {hash(qi):qi,hash(w):w} # Set of quaternions
newQuat = list([x])
z = 0
while(len(newQuat) > 0 and z < 70):
	print('z=',z)
	z = z+1
	x = newQuat.pop()
	hx = hash(x)
	if(hx not in rQuat):
	  l = 0
	  rQuat[hx] = x
	  for y in rQuat.values():
		  print 'Begin multi' 
		  result = x*y
		  hr = hash(result)
		  print 'End Mltiplication'
		  if(hr not in rQuat):
			  newQuat.append(result)
			  #print result
			  print 'new Quaterion'
			  print ('#new: ',len(newQuat))
			  print ('#Quat: ',len(rQuat))
		  else:
		    l+1
		  print 'ende suche'
	  print('Doppele berechnet:',l)
