require 'test_helper'

class NotificationControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get notification_index_url
    assert_response :success
  end

  test "should get show" do
    get notification_show_url
    assert_response :success
  end

  test "should get create" do
    get notification_create_url
    assert_response :success
  end

  test "should get new" do
    get notification_new_url
    assert_response :success
  end

  test "should get destroy" do
    get notification_destroy_url
    assert_response :success
  end

end
