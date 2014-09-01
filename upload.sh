#!/bin/bash

HOST=172.16.0.3  		 #This is the FTP servers host or IP address.
USER=jijeshm             #This is the FTP user that has access to the server.
PASS=haha         #This is the password for the FTP user.

# Call 1. Uses the ftp command with the -inv switches.  -i turns off interactive     prompting. -n Restrains FTP from attempting the auto-login feature. -v enables verbose and progress.
rm -f /tmp/ftp.log
ftp -inv $HOST <<EOF > /tmp/ftp.log
user $USER $PASS
cd CSVS
mput *.csv
bye
EOF

echo "Done"
FTP_SUCCESS_MSG="221 Thank you for using the FTP"
fgrep "$FTP_SUCCESS_MSG" /tmp/ftp.log > /dev/null

OUT=$?
if [ $OUT -eq 0 ];then
   echo "ftp OK"
else
   echo "ftp Error: "$OUT
   exit 1
fi
rm *.csv
echo "FINISHED"