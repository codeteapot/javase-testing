#!/bin/bash

old_version=$1
new_version=$2

if [[ "${new_version}x" = "x" ]]
then
  echo "New version was not specified"
  exit 1
fi

pushd ..

git grep -l "$old_version" | xargs sed -i "s/$old_version/$new_version/g"
git add .
git commit -m "Release v$new_version"
git tag "v$new_version"
git reset HEAD~1
git checkout -- .

git grep -l "$old_version" | xargs sed -i "s/$old_version/$new_version-SNAPSHOT/g"
git add .
git commit -m "Release v$new_version"

popd
