require 'test_helper'

class BuildingControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get building_index_url
    assert_response :success
  end

  test "should get show" do
    get building_show_url
    assert_response :success
  end

  test "should get create" do
    get building_create_url
    assert_response :success
  end

  test "should get new" do
    get building_new_url
    assert_response :success
  end

  test "should get destroy" do
    get building_destroy_url
    assert_response :success
  end

end
