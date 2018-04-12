#/*
# * Copyright (c) 2018 JP-L, https://www.jp-l.org/
# *
# * Permission is hereby granted, free of charge, to any person obtaining
# * a copy of this software and associated documentation files (the
# * "Software"), to deal in the Software without restriction, including
# * without limitation the rights to use, copy, modify, merge, publish,
# * distribute, sublicense, and/or sell copies of the Software, and to
# * permit persons to whom the Software is furnished to do so, subject to
# * the following conditions:
# *
# * The above copyright notice and this permission notice shall be
# * included in all copies or substantial portions of the Software.
#
# * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
# * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
# * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
# * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#*/

data "terraform_remote_state" "environment" {
	backend = "s3"
	workspace = "${terraform.workspace}"
	
	config {
		bucket = "jpl-tfstate"
		key    = "terraform/state"
		region = "eu-west-1"
	}
}

module "default_region" {
	source = "./region"
	
	region			= "${data.terraform_remote_state.environment.default_region}"
	
	#vpc_id			= "${data.terraform_remote_state.environment.vpc_ids[format("%s_%s", data.terraform_remote_state.environment.region["default_region"],"vpc_id")]}"
	vpc_id			= "${data.terraform_remote_state.environment.vpc_ids[format("%s_%s", data.terraform_remote_state.environment.default_region,"vpc_id")]}"

#	vpc_id			= "${data.terraform_remote_state.environment.vpc_ids[format("%s_%s", "eu-central-1","vpc_id")]}"
	alb_config		= "${data.terraform_remote_state.environment.alb_config}"
	applications	= "${var.applications}"
}

#module "region_eu_west_1" {
#	source = "./region"

#	region			= "${element(var.regions, 0)}"
#	applications	= "${var.applications}"	
#}

