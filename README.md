# BC Capstone - Github Action CI/CD Demo
Group Members: Hieu Do, Matthew Granger, Joe Prado, Elena Ochkina

### Purpose:
Demonstrate how Github Actions can be leveraged to automate CI/CD. This demo will include code to run tests, build and 'deploy' artifacts to AWS. If you're interested in the workflow demoed in class to deploy a Springboot Application to AWS ECS, please contact a member of Capstone Group 6. 

### Tutorial:
1. Fork this repository

2. Navigate to the 'Action' tab of your fork and if needed, enable workflows to be run on this forked repository.

3. Once the workflow is enabled, notice a workflow named 'Build & Deploy' on the left panel. This workflow exists because there is a `deploy_build.yaml` file in the .github/workflows folder

4. Manually trigger the workflow: Click on the workflow name, select the 'Run workflow' dropdown button, confirm that branch = main, and click the green 'Run Workflow' button. `deploy_build.yaml` was configured such that you can trigger the workflow manually. Notice that `deploy_build.yaml` also configured the workflow to be trigged on push to main

5. Test trigger upon 'push to main' event: Clone your forked repository locally, make a minor change (such as adding your name to this README), and push to your remote. Notice that another workflow run was triggered on push and that this workflow should run to completion.

6. To ensure that code that fails to build and pass pass tests never gets deployed, you can include the build and test steps as part of your code deployment protocol. In `build_deploy.yaml`, this is achieved by running `mvn clean test` before `mvn clean package`. Purposefully write a test that will fail in src/test/java/com/example/CalculatorTest.java and push that change to remote. Another workflow run should be triggered but will fail before the Jars can be packaged for deployment.

        Example: change `calculatorInitialValueZero()` to
        ```
        @Test
        public void calculatorInitialValueZero() {
            Calculator calculator = new Calculator();
            assertEquals(1, calculator.getValue());
        }
        ```

7. [OPTIONAL] Github Actions and AWS: Github Actions can be used to deploy code to AWS. To demonstrate this, we will put JARS to an AWS S3 bucket where they can be deployed on Elastic Beanstalk, Elastic Container Service, etc.

    a. Create a 'github-action' IAM user by following https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html, section 'Creating IAM users (console)'. Attach a Full S3 Access policy to the user. Take note of the AWS access key id and key value.

    b. Add credentials to Github as Github Secrets:

        - Go to your Github fork > Settings > Secrets and variables > Actions
        - Create a New Repository Secret for the Access Key ID. Name = `AWS_KEY_ID` and Secret = value copied from console.
        - Repeat the previous step for the Access Key. Name it `AWS_SECRET_ACCESS_KEY`

    c. The credentials in step b are used by the `configure-aws-credentials` action in `build_deployment.yaml` workflow file. This action used to connect to AWS (https://github.com/aws-actions/configure-aws-credentials). Confirm that the name of the secrets referenced  are consistent with your secret configurations in Github. 

    d. Create an S3 bucket -- instructions at https://docs.aws.amazon.com/AmazonS3/latest/userguide/creating-bucket.html. 

    d. Update `build_deployment.yaml`:

        - Change the `aws region` parameter used by the `configure-aws-credentials` action to reflect the region your bucket exists in.
        - Specify the bucket name used by the `aws s3 cp ...` command. If your bucket is named 'test-bucket-github-action-demo', the command should be `aws s3 cp dist.zip s3://test-bucket-github-action-demo`.

    e. Commit changes and push to remote fork

8. Notice the status of the workflow run triggered by the push in step 7e. It should run to completion. Once it does, check your bucket and note that it now contains a 'dist' folder object.





