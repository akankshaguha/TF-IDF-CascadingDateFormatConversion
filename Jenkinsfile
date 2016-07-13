
stage 'setJAVA_HOME'
node {

     env.JAVA_HOME = "C:\\Program Files\\Java\\jdk1.8.0_45"
   
     echo env.JAVA_HOME
}
node{
git 'https://github.com/exorcist007/TDF-IDF-CascadingDateFormatConversion.git'
bat 'gradle build --info'
}
node{
git 'https://github.com/exorcist007/TDF-IDF-CascadingDateFormatConversion.git'
bat 'gradle sayHello'
}
