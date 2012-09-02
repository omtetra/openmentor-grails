Summary: A web-based application to help support tutor feedback
Name: openmentor
Version: 1.0.M3
Release: 1
Source0: openmentor-1.0.M3.tar.gz
License: ASL 2.0
Group: Applications/Internet
Packager: Stuart Watt <stuart@morungos.com>
requires: tomcat6 > 6.0
BuildArch: noarch
BuildRoot: %{_tmppath}/%{name}-buildroot
%description
This is a web-based application designed to help evaluate use of feedback 
in electronically marked assignments. It extracts comments from Word files 
and classifies them automatically, then offering a variety of ways of 
visualizing and analyzing the results.

%prep

%setup -q

%build
grails war

%install
install -m 0755 -d $RPM_BUILD_ROOT/var/lib/tomcat6/webapps/
install -m 0755 -d $RPM_BUILD_ROOT/var/log/openmentor
install -m 0755 -d $RPM_BUILD_ROOT/etc/openmentor
install -m 0755 target/openmentor-1.0.M3.war $RPM_BUILD_ROOT/var/lib/tomcat6/webapps/
install -m 0755 src/templates/conf/openmentor-config.groovy $RPM_BUILD_ROOT/etc/openmentor/openmentor-config.groovy
%clean
rm -rf $RPM_BUILD_ROOT
%post
echo "OpenMentor web application installed OK"
%files
%defattr(0555, tomcat, tomcat, 0755) 
%dir /var/lib/tomcat6/webapps/
%dir /var/log/openmentor
%dir %attr(0555, -, -) /etc/openmentor
/var/lib/tomcat6/webapps/openmentor-1.0.M3.war
/etc/openmentor/openmentor-config.groovy
