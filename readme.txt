      The intent of this project is to develop a product which would be useful in capturing the various stages of a project life cycle. Any software project goes through a certain defined stages right from the conceptualization phase to the execution phase. This system captures all the desired information and helps the team to be much more efficient and productive. This system will be developed iteratively in 3 phases corresponding to the project deadlines set in class. More functionality will be added with each working version. This system falls under the categories of Business Requirements Gathering System, Team Collaboration System, Document Repository, Bug Tracking System, Time Entry System, and Resource Planning System. The early versions are for educational purposes, as examples of software engineering practice, and as open source on which more functionality can be plugged in. The later versions are expected to be a commercial product marketed by software organization. 
Git Basics from (https://github.com/heckendorfc/plm/blob/development/documentation/PlanningPhase/CS673F13P4_SPMP.pdf)

* When you first start working on the project, you'll want to make a local copy of the repository to keep track of your changes:
      git clone https :// github.com / heckendorfc /plm. git

* Before you begin working, you should create a feature branch based on the development branch:
      git checkout -b somefeature origin / development

* Once you have some code written and want to save your work to the repo, tell git which files you would like to commit to the repo and commit them:
      git add file1 file2 ...
      git commit -m " commit message here "

* To get the latest code that other team members have shared on the github repo:
      git fetch origin
      git pull

* After you have one or more commits in your local repo, you'll want to share them with the rest of the team:
      git push -u origin somebranch

Further reading: http://git-scm.com/book

