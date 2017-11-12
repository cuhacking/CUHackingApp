class UpdateEventTime < ActiveRecord::Migration[5.1]
  def up
  	remove_column :events, :date
  	change_column :events, :start_time, :datetime
  	change_column :events, :end_time, :datetime
  end

  def down
  	add_column :events, :date, :date
  	change_column :events, :start_time, :time
  	change_column :events, :end_time, :time
  end
end
