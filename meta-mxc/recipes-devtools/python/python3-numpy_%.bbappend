DEPENDS += "python3-cython-native"

do_patch:append() {
    bb.build.exec_func('do_fix_runtime_library_check', d)
}

python do_fix_runtime_library_check() {
    with open(d.getVar("S")+"/numpy/distutils/system_info.py", "r+") as f:
        lines = f.readlines()
        for index in range(len(lines)):
            if "def get_runtime_lib_dirs" in lines[index]:
                lines.pop(index + 1)
                lines.pop(index + 1)
                lines.pop(index + 1)
                line = lines.pop(index + 1)
                line = line.replace("path", "[]")
                lines.insert(index+1,line)
                break

    with open(d.getVar("S")+"/numpy/distutils/system_info.py", "w") as f:
        f.writelines(lines)
}
