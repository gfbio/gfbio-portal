# GFBio-Portal

This is a repository for installed packages of GFBio portal related. On GFBio portal, we provide contents, news, tutorials, and tools to help our portal users manage their biological data.

Under this level, the repository is splitted as:
  - gfbio_release_package
  - mockups

## gfbio_release_package
Here, each released version is put into different folder, following this notation:
>v[a].[b]_YYYYMMDD, 

where [a] is the major version, and [b] is the minor version.
For example: v7.0_20170427

### Each release package
We categorize the script/installation packages according to their functions. They are:
  - db (database scripts)
  - hook (.war)
  - page (.lar)
  - portlets (.war)
  - themes (.war)
  - gadgets (.xml)
  - changeslog.txt

Some folders might not appear in some releases, depend on the change compare to the last release. However, the changeslog.txt must be updated in every release and contains only the list of new features/bug fixes.

All the packages in this repository are tested on gfbio-pub2 and rolled out to gfbio.org on the date of release. Please note that there could be some settings that make the packages incompatible with gfbio-dev1.
  
## mockups
Any new design documents can be shared here. However, most of our documents are stored internally http://fusion.cs.uni-jena.de/git/gfbio/ Please contact us if you need more information.

# TO-DO
Mockups folder has not been updated for 3 years. And besides our private git repository, we document the design on wiki pages. Please consider to remove this.
