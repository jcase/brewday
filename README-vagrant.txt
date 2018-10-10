We run mongdb in a Vagrant container

see setup/vagrant/Vagrantfile

To start the Vagrant VM instance:

To check status
VAGRANT_CWD=./setup/vagrant global-status

You may need to prune first, to eliminate bad cached data
VAGRANT_CWD=./setup/vagrant global-status --prune

To suspend  (fast boot, takes up a lot of disk space)
VAGRANT_CWD=./setup/vagrant suspend

Guest OS shutdown  (slower boot)
VAGRANT_CWD=./setup/vagrant halt

If you want to reset the Vagrant machine (and start over from scratch)
VAGRANT_CWD=./setup/vagrant destroy

To start (from a shutdown state) or to initialize (if needed fresh):
VAGRANT_CWD=./setup/vagrant vagrant up

TODO: command to create a ready-to-use machine image that we can reset to, so that we can effectively wipe
the mongodata and not have to re-download and reinit the whole thing.
