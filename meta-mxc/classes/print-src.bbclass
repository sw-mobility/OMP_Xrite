python do_print_src () {
    # should probably be indented
    srcuri = d.getVar('SRC_URI', True).split()
    bb.warn("SRC_URI look like: %s" % srcuri)

    bdir = d.getVar('TOPDIR', True)
    with open("%s/src_uris" % bdir, 'a+') as f:
        f.write(','.join(srcuri) + "\n")
}

addtask do_print_src before do_fetch
