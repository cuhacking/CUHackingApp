require 'test_helper'

class HelpRequestControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get help_request_index_url
    assert_response :success
  end

  test "should get show" do
    get help_request_show_url
    assert_response :success
  end

  test "should get create" do
    get help_request_create_url
    assert_response :success
  end

  test "should get new" do
    get help_request_new_url
    assert_response :success
  end

  test "should get destroy" do
    get help_request_destroy_url
    assert_response :success
  end

end
