print("--- Testing Area Generator ---\n")

VALID = ["EMP","LAD","HDR","HOL"]
ALL = VALID + ["MTL","PLT"]

print("--- Movement ---")
print("Started!")
for start in VALID:
    for up in ALL:
        for down in ALL:
            for left in ALL:
                for right in ALL:
                    oFile = open("Movement/" + "_".join([start,up,down,left,right]), 'w')
                    oFile.write("3 4\n")
                    oFile.write(" ".join(["EMP",up,"EMP\n"]))
                    oFile.write(" ".join([left,start,right + '\n']))
                    oFile.write(" ".join(["EMP",down,"EMP\n"]))
                    oFile.write("MTL MTL MTL\n")

print("Finished!")