#!/bin/bash

old_version=$1
new_version=$2

if [[ "${new_version}x" = "x" ]]
then
  echo "New version was not specified"
  exit 1
fi

git_version_replace() {
  local prev_version=$1
  local next_version=$2 
  
  git grep -l "$prev_version" -- '*pom.xml' | xargs \
  sed -i "s/<version>$prev_version<\/version>/<version>$next_version<\/version>/g"
  git grep -l "$prev_version" -- '*.md' | xargs \
  sed -i "s/$prev_version/$next_version/g"
  git add .
  git commit -m "Release v$new_version"
}

pushd ..

git_version_replace "$old_version" "$new_version"
git tag "v$new_version"
git reset HEAD~1
git checkout -- .
git_version_replace "$old_version" "$new_version-SNAPSHOT"

popd
